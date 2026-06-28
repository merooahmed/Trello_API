package com.terrelloAPI.constants;

public class EndPoints {
    public static final String CREATE_BOARD = "/boards/";
    public static final String DELETE_BOARD = "/boards/{id}";
    public static final String CREATE_CARD="/cards";
    public static final String GET_CARDS="/boards/{boardId}/cards";
    public static final String DELETE_CARD="/cards/{cardId}";
    public static final String DEFAULT_LISTS="/boards/{boardId}/lists";
}
