package com.example.magnificentchef.view.onboarding;

public class BoardingInformation{
    private final int firstCircle;
    private final int seconedCircle;
    private final int thirdCircle;
    private final int image;
    private final String description;
    private final int position;

    public BoardingInformation(int firstCircle, int seconedCircle, int thirdCircle, int image, String description,int position) {
        this.firstCircle = firstCircle;
        this.seconedCircle = seconedCircle;
        this.thirdCircle = thirdCircle;
        this.image = image;
        this.description = description;
        this.position=position;
    }

    public int getFirstCircle() {
        return firstCircle;
    }

    public int getSeconedCircle() {
        return seconedCircle;
    }

    public int getThirdCircle() {
        return thirdCircle;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
    public int getPosition() {
        return position;
    }



}

