package com.example.cookit;

public class Message {

    private  String message,userId,userName,recipeKey,recipeName;
    public Message(){

    }
    public Message(String message,String userId,String userName,String recipeKey,String recipeName){
        this.message = message;
        this.userId = userId;
        this.userName = userName;
        this.recipeKey = recipeKey;
        this.recipeName = recipeName;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecipeKey() {
        return recipeKey;
    }

    public void setRecipeKey(String recipeKey) {
        this.recipeKey = recipeKey;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }
}
