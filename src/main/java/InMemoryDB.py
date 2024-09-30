# class InMemoryDB:
#     def __init__(self):
#         self.tables = {}

#     def create_table(self, table_name: str, columns: list):
#         """Create a table with specified columns"""
#         self.tables[table_name] = Table(table_name, columns)

#     def insert(self, table_name: str, row: dict):
#         """Insert a row into a specified table"""
#         if table_name in self.tables:
#             self.tables[table_name].insert(row)
#         else:
#             raise ValueError("Table does not exist")

#     def select(self, table_name: str, conditions: dict = None):
#         """Retrieve data from the table based on conditions"""
#         if table_name in self.tables:
#             return self.tables[table_name].select(conditions)
#         else:
#             raise ValueError("Table does not exist")

#     def update(self, table_name: str, updates: dict, conditions: dict = None):
#         """Update data in the table based on conditions"""
#         if table_name in self.tables:
#             self.tables[table_name].update(updates, conditions)
#         else:
#             raise ValueError("Table does not exist")

#     def delete(self, table_name: str, conditions: dict):
#         """Delete data from the table based on conditions"""
#         if table_name in self.tables:
#             self.tables[table_name].delete(conditions)
#         else:
#             raise ValueError("Table does not exist")


# class Table:
#     def __init__(self, name: str, columns: list):
#         self.name = name
#         self.columns = columns
#         self.rows = []

#     def insert(self, row: dict):
#         """Insert a row if it matches the table's columns"""
#         if all(column in row for column in self.columns):
#             self.rows.append(row)
#         else:
#             raise ValueError("Row does not match table columns")

#     def select(self, conditions: dict = None):
#         """Retrieve rows based on conditions (e.g., column values)"""
#         if conditions:
#             return [row for row in self.rows if self._match(row, conditions)]
#         else:
#             return self.rows

#     def update(self, updates: dict, conditions: dict = None):
#         """Update rows based on conditions"""
#         for row in self.rows:
#             if self._match(row, conditions):
#                 for key, value in updates.items():
#                     if key in row:
#                         row[key] = value

#     def delete(self, conditions: dict):
#         """Delete rows based on conditions"""
#         self.rows = [row for row in self.rows if not self._match(row, conditions)]

#     def _match(self, row: dict, conditions: dict):
#         """Check if a row matches the given conditions"""
#         if not conditions:
#             return True
#         return all(row.get(key) == value for key, value in conditions.items())




# import threading

# class ThreadSafeTable(Table):
#     def __init__(self, name: str, columns: list):
#         super().__init__(name, columns)
#         self.lock = threading.Lock()

#     def insert(self, row: dict):
#         with self.lock:
#             super().insert(row)

#     def select(self, conditions: dict = None):
#         with self.lock:
#             return super().select(conditions)

#     def update(self, updates: dict, conditions: dict = None):
#         with self.lock:
#             super().update(updates, conditions)

#     def delete(self, conditions: dict):
#         with self.lock:
#             super().delete(conditions)








# db = InMemoryDB()
# db.create_table("users", ["id", "name", "age"])

# db.insert("users", {"id": 1, "name": "John", "age": 30})
# db.insert("users", {"id": 2, "name": "Jane", "age": 25})

# results = db.select("users", {"age": 25})
# print(results)  # [{'id': 2, 'name': 'Jane', 'age': 25}]

# db.update("users", {"age": 26}, {"id": 2})
# db.delete("users", {"id": 1})






from enum import Enum
from typing import List, Dict, Any


class ColumnType(Enum):
    INT = 'INT'
    STRING = 'STRING'


class Column:
    def __init__(self, column_name: str, column_type: ColumnType):
        self.column_name = column_name
        self.column_type = column_type

    def get_column_name(self) -> str:
        return self.column_name


class Row:
    def __init__(self, row_id: int, column_data: Dict[Column, Any]):
        self.row_id = row_id
        self.column_data = column_data

    def get_row_id(self) -> int:
        return self.row_id

    def get_column_data(self) -> Dict[Column, Any]:
        return self.column_data


class Table:
    def __init__(self, name: str, columns: List[Column]):
        self.auto_increment_id = 1
        self.name = name
        self.column_map: Dict[str, Column] = {col.get_column_name(): col for col in columns}
        self.rows: List[Row] = []

    def truncate_rows(self):
        self.rows.clear()

    def insert_row(self, column_values: Dict[Column, Any]):
        for column in column_values.keys():
            if not self.check_if_column_exists(column.get_column_name()):
                return
        row_id = self.get_auto_increment_id()
        row = Row(row_id, column_values)
        self.rows.append(row)

    def print_rows(self):
        print(f"Printing all rows for Table: {self.name}")
        self.print_records(self.rows)

    def get_records_by_column_value(self, column: Column, value: Any):
        matching_rows = [row for row in self.rows if row.get_column_data().get(column) == value]
        print(f"Printing matching rows for Table: {self.name}")
        self.print_records(matching_rows)

    def print_records(self, rows: List[Row]):
        print("\t".join([col_name for col_name in self.column_map.keys()]))
        for row in rows:
            print(f"{row.get_row_id()}.", end="")
            for column, value in row.get_column_data().items():
                print(f"\t{value}", end="")
            print()

    def get_auto_increment_id(self) -> int:
        current_id = self.auto_increment_id
        self.auto_increment_id += 1
        return current_id

    def check_if_column_exists(self, column_name: str) -> bool:
        if column_name not in self.column_map:
            print(f"Table: {self.name} does not contain column: {column_name}")
            return False
        return True


class Database:
    def __init__(self, name: str):
        self.name = name
        self.table_map: Dict[str, Table] = {}

    def create_table(self, table_name: str, columns: List[Column]):
        if self.check_if_table_exists(table_name):
            print(f"Table {table_name} already exists!")
            return
        table = Table(table_name, columns)
        self.table_map[table_name] = table

    def drop_table(self, table_name: str):
        if not self.check_if_table_exists(table_name):
            return
        del self.table_map[table_name]
        print(f"Table {table_name} dropped!")

    def truncate_table(self, table_name: str):
        if not self.check_if_table_exists(table_name):
            return
        self.table_map[table_name].truncate_rows()

    def insert_table_row(self, table_name: str, column_values: Dict[Column, Any]):
        if not self.check_if_table_exists(table_name):
            return
        self.table_map[table_name].insert_row(column_values)

    def print_table_all_rows(self, table_name: str):
        if not self.check_if_table_exists(table_name):
            return
        self.table_map[table_name].print_rows()

    def filter_table_records_by_column_value(self, table_name: str, column: Column, value: Any):
        if not self.check_if_table_exists(table_name):
            return
        self.table_map[table_name].get_records_by_column_value(column, value)

    def check_if_table_exists(self, table_name: str) -> bool:
        if table_name not in self.table_map:
            print(f"Table {table_name} does not exist")
            return False
        return True


# Client code to test the in-memory RDMS

def main():
    name_column = Column("name", ColumnType.STRING)
    age_column = Column("age", ColumnType.INT)
    salary_column = Column("salary", ColumnType.INT)

    db = Database("MyDB")

    columns = [name_column, age_column, salary_column]
    db.create_table("Employee", columns)

    # Insert first row
    column_values_1 = {name_column: "John", age_column: 25, salary_column: 10000}
    db.insert_table_row("Employee", column_values_1)

    # Insert second row
    column_values_2 = {name_column: "Kim", age_column: 28, salary_column: 12000}
    db.insert_table_row("Employee", column_values_2)

    # Print all rows
    db.print_table_all_rows("Employee")

    # Filter rows by age
    db.filter_table_records_by_column_value("Employee", age_column, 28)

    # Filter rows by name
    db.filter_table_records_by_column_value("Employee", name_column, "John")

    # Truncate and drop the table
    db.truncate_table("Employee")
    db.drop_table("Employee")

    # Attempt to print again (should show nothing)
    db.print_table_all_rows("Employee")


if __name__ == "__main__":
    main()