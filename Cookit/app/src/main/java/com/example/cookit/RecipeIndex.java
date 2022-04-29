package com.example.cookit;

public class RecipeIndex {
    public String name,category,recipeName,purl;
    public RecipeIndex() {}
    public RecipeIndex(String name,String category,String recipeName,String purl){
        this.name = name;
        this.category = category;
        this.recipeName = recipeName;
        this.purl = purl;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getRecipeName(){
        return recipeName;
    }
    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }
    public String getPurl(){ return purl; }
    public void setPurl(String purl){
        this.purl = purl;
    }
}

