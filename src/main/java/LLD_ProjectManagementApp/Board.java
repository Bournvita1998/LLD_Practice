package LLD_ProjectManagementApp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    private String boardId;
    private String name;
    private String privacy;
    private String url;
    private List<User> members;
    private List<TaskList> lists;

    public Board(String name, String privacy) {
        this.boardId = UUID.randomUUID().toString();
        this.name = name;
        this.privacy = privacy;
        this.url = "http://example.com/" + this.boardId;
        this.members = new ArrayList<>();
        this.lists = new ArrayList<>();
    }

    public String getBoardId() {
        return boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getUrl() {
        return url;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(String userId) {
        members.removeIf(user -> user.getUserId().equals(userId));
    }

    public List<TaskList> getLists() {
        return lists;
    }

    public void addList(TaskList list) {
        lists.add(list);
    }

    public void removeList(String listId) {
        lists.removeIf(list -> list.getListId().equals(listId));
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardId='" + boardId + '\'' +
                ", name='" + name + '\'' +
                ", privacy='" + privacy + '\'' +
                ", url='" + url + '\'' +
                ", members=" + members +
                ", lists=" + lists +
                '}';
    }
}

