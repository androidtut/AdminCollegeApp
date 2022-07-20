package com.example.admincollegeapp.Faculty;

public class teacherdata {
    String name,email,post,category,key,image;

    public teacherdata(String name, String email, String post, String category, String key, String image) {
        this.name = name;
        this.email = email;
        this.post = post;
        this.category = category;
        this.key = key;
        this.image = image;
    }

   public teacherdata(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
