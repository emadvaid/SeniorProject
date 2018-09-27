package com.ALCverificationtool.models;

import java.util.List;

public class GetUsersResponse {
    private List<User> users;

    public GetUsersResponse() {
    }

    public GetUsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

