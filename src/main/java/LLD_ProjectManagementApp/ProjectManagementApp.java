package LLD_ProjectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class ProjectManagementApp {
    private List<User> users;
    private List<Board> boards;

    public ProjectManagementApp() {
        this.users = new ArrayList<>();
        this.boards = new ArrayList<>();
    }

    public void createUser(String name, String email) {
        User user = new User(name, email);
        users.add(user);
    }

    public void createBoard(String name, String privacy) {
        Board board = new Board(name, privacy);
        boards.add(board);
        System.out.println("Created board: " + board.getBoardId());
    }

    public void deleteBoard(String boardId) {
        boards.removeIf(board -> board.getBoardId().equals(boardId));
    }

    public void showAllBoards() {
        for (Board board : boards) {
            System.out.println(board);
        }
    }

    public void showBoard(String boardId) {
        for (Board board : boards) {
            if (board.getBoardId().equals(boardId)) {
                System.out.println(board);
                return;
            }
        }
        System.out.println("Board " + boardId + " does not exist");
    }

    // Other methods to handle list and card operations

    public static void main(String[] args) {
        ProjectManagementApp app = new ProjectManagementApp();
        app.createUser("John Doe", "john@example.com");
        app.createUser("Jane Doe", "jane@example.com");

        app.createBoard("Work", "PUBLIC");
        app.showAllBoards();
    }
}

