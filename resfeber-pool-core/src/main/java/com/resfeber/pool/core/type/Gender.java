package com.resfeber.pool.core.type;


public enum Gender {
    MALE("male"), FEMALE("female");

    private String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public static Gender getGender(String value) {
        Gender gender = null;
        for (Gender gender1 : Gender.values()) {
            if (gender1.gender.equals(value)) {
                gender = gender1;
                break;
            }
        }

        return gender;
    }
}
