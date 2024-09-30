class Document:
    def __init__(self, text):
        self.text = text

    def count_term(self, term):
        # Count the number of occurrences of the term in the document
        return self.text.lower().split().count(term.lower())


class Dataset:
    def __init__(self):
        self.documents = []

    def add_document(self, text):
        # Add a new document to the dataset
        self.documents.append(Document(text))

    def search_documents(self, term):
        # Search through documents and return a list of tuples (document, count of term)
        results = []
        for doc in self.documents:
            count = doc.count_term(term)
            if count > 0:
                results.append((doc, count))
        # Sort by the count of the search term (higher count first)
        results.sort(key=lambda x: x[1], reverse=True)
        return results


class SearchEngine:
    def __init__(self):
        self.datasets = {}

    def create_dataset(self, name):
        # Create a new dataset
        self.datasets[name] = Dataset()

    def insert_document(self, dataset_name, document):
        # Insert a document into the specified dataset
        if dataset_name in self.datasets:
            self.datasets[dataset_name].add_document(document)
        else:
            raise ValueError(f"Dataset {dataset_name} does not exist.")

    def search(self, dataset_name, term):
        # Search for the term in the specified dataset
        if dataset_name in self.datasets:
            results = self.datasets[dataset_name].search_documents(term)
            return [doc.text for doc, count in results]
        else:
            raise ValueError(f"Dataset {dataset_name} does not exist.")


# Example usage
search_engine = SearchEngine()

# Create a dataset
search_engine.create_dataset('TechBlog')

# Insert documents into the dataset
search_engine.insert_document('TechBlog', 'apple is a fruit')
search_engine.insert_document('TechBlog', 'apple apple come on')
search_engine.insert_document('TechBlog', 'oranges are sour')
search_engine.insert_document('TechBlog', 'apple is sweet')
search_engine.insert_document('TechBlog', 'veggies are healthy')

# Search for the term 'apple'
results = search_engine.search('TechBlog', 'apple')
print(results)
