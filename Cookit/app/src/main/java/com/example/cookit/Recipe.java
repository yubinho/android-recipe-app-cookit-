package com.example.cookit;

public class Recipe {
    public String name,category,recipeName,recipeInfo,recipeIngredient,step1,step2,step3,step4,step5,step6,step7,step8,step9,step10,purl,uid,imageStep1,imageStep2,imageStep3,imageStep4,imageStep5,imageStep6,imageStep7,imageStep8,imageStep9,imageStep10,checked;
    public double calorie;
    public int clickCount;
    public Recipe() {}
    public Recipe(String name,String category,String recipeName,String recipeInfo,
                  String recipeIngredient,String step1,String step2,String step3,String step4,String step5,String step6,String step7,String step8,String step9,String step10,
                  String purl,String uid,String imageStep1,String imageStep2,String imageStep3,String imageStep4,String imageStep5,String imageStep6,String imageStep7,String imageStep8,String imageStep9,String imageStep10,String checked,double calorie,int clickCount){
        this.name = name;
        this.category = category;
        this.recipeName = recipeName;
        this.recipeInfo = recipeInfo;
        this.recipeIngredient = recipeIngredient;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
        this.step5 = step5;
        this.step6 = step6;
        this.step7 = step7;
        this.step8 = step8;
        this.step9 = step9;
        this.step10 = step10;
        this.imageStep1 = imageStep1;
        this.imageStep2 = imageStep2;
        this.imageStep3 = imageStep3;
        this.imageStep4 = imageStep4;
        this.imageStep5 = imageStep5;
        this.imageStep6 = imageStep6;
        this.imageStep7 = imageStep7;
        this.imageStep8 = imageStep8;
        this.imageStep9 = imageStep9;
        this.imageStep10 = imageStep10;
        this.purl = purl;
        this.uid = uid;
        this.checked = checked;
        this.calorie = calorie;
        this.clickCount = clickCount;
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
    public String getRecipeInfo(){
        return recipeInfo;
    }
    public void setRecipeInfo(String recipeInfo){
        this.recipeInfo = recipeInfo;
    }
    public String getRecipeIngredient(){
        return recipeIngredient;
    }
    public void setRecipeIngredient(String recipeIngredient){ this.recipeIngredient = recipeIngredient; }
    public String getStep1(){
        return step1;
    }
    public void setStep1(String step1){
        this.step1 = step1;
    }
    public String getStep2(){
        return step2;
    }
    public void setStep2(String step2){
        this.step2 = step2;
    }
    public String getStep3(){
        return step3;
    }
    public void setStep3(String step3){
        this.step3 = step3;
    }
    public String getStep4(){
        return step4;
    }
    public void setStep4(String step4){
        this.step4 = step4;
    }
    public String getStep5(){
        return step5;
    }
    public void setStep5(String step5){
        this.step5 = step5;
    }
    public String getStep6(){
        return step6;
    }
    public void setStep6(String step6){
        this.step6 = step6;
    }
    public String getStep7(){
        return step7;
    }
    public void setStep7(String step7){
        this.step7 = step7;
    }
    public String getStep8(){
        return step8;
    }
    public void setStep8(String step8){
        this.step8 = step8;
    }
    public String getStep9(){
        return step9;
    }
    public void setStep9(String step9){
        this.step9 = step9;
    }
    public String getStep10(){
        return step10;
    }
    public void setStep10(String step5){
        this.step10 = step10;
    }
    public String getImageStep1(){
        return imageStep1;
    }
    public void setImageStep1(String imageStep1){
        this.imageStep1 = imageStep1;
    }
    public String getImageStep2(){
        return imageStep2;
    }
    public void setImageStep2(String imageStep2){
        this.imageStep2 = imageStep2;
    }
    public String getImageStep3(){
        return imageStep3;
    }
    public void setImageStep3(String imageStep3){
        this.imageStep3 = imageStep3;
    }
    public String getImageStep4(){
        return imageStep4;
    }
    public void setImageStep4(String imageStep4){
        this.imageStep4 = imageStep4;
    }
    public String getImageStep5(){
        return imageStep5;
    }
    public void setImageStep5(String imageStep5){
        this.imageStep5 = imageStep5;
    }
    public String getImageStep6(){
        return imageStep6;
    }
    public void setImageStep6(String imageStep6){
        this.imageStep6 = imageStep6;
    }

    public String getImageStep7(){
        return imageStep7;
    }
    public void setImageStep7(String imageStep7){
        this.imageStep7 = imageStep7;
    }

    public String getImageStep8(){
        return imageStep8;
    }
    public void setImageStep8(String imageStep8){
        this.imageStep8 = imageStep8;
    }

    public String getImageStep9(){
        return imageStep9;
    }
    public void setImageStep9(String imageStep9){
        this.imageStep9 = imageStep9;
    }

    public String getImageStep10(){
        return imageStep10;
    }
    public void setImageStep10(String imageStep10){
        this.imageStep10 = imageStep10;
    }
    public String getChecked(){
        return checked;
    }
    public void setChecked(String checked){
        this.checked = checked;
    }

    public String getPurl(){
        return purl;
    }
    public void setPurl(String purl){
        this.purl = purl;
    }
    public String getUid(){ return uid; }
    public void setUid(String uid){ this.uid = uid; }
    public double getCalorie(){ return calorie; }
    public void setCalorie(double calorie){ this.calorie = calorie; }
    public void setClickCount(int clickCount){ this.clickCount = clickCount;}
    public int getClickCount(){return clickCount;}
}
