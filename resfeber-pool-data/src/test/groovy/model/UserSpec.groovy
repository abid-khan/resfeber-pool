package com.resfeber.pool.data.model

import spock.lang.Shared
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory


class UserSpec extends Specification {

    @Shared
    Validator validator

    def setupSpec() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "Email is empty"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "email.not.empty"

        where:

        email | firstName   | lastName
        ""    | "firstName" | "lastName"
    }

    def "Email length is more than 255"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "email.max.size"

        where:
        email                                                                                                                                                                                                                                                              | firstName   | lastName
        "annxfsdbzclkssinjowrzxyezalfobjxagffutbdgrufzdsijrkswlulbqzygseaggfnwuvnxjtjppzngeznhnnmixdraxwqwjrhbjsnvsvfpsmglnngocleipqugycvfbqvbhrhgqdnjvvhehddaojdicivlirgkjyolptjganzvimgqqbjuwqhopkrglormhrnrparffyqwirfuaienrcbwgypaiylfycutvyfwtmwenbtmjszrkociylponnb" | "firstName" | "lastName"


    }

    def "firstName is null"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "firstName.not.null"

        where:

        email            | firstName | lastName
        "test@gmail.com" | null      | "lastName"


    }

    def "firstName length is more than 255"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "firstName.max.size"

        where:

        email            | firstName                                                                                                                                                                                                                                                          | lastName
        "test@gmail.com" | "annxfsdbzclkssinjowrzxyezalfobjxagffutbdgrufzdsijrkswlulbqzygseaggfnwuvnxjtjppzngeznhnnmixdraxwqwjrhbjsnvsvfpsmglnngocleipqugycvfbqvbhrhgqdnjvvhehddaojdicivlirgkjyolptjganzvimgqqbjuwqhopkrglormhrnrparffyqwirfuaienrcbwgypaiylfycutvyfwtmwenbtmjszrkociylponnb" | "lastName"


    }

    def "lastName is null"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "lastName.not.null"

        where:

        email            | firstName   | lastName
        "test@gmail.com" | "firstName" | null
    }


    def "lastName length is more than 255"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == false
        assert violations.iterator().next().getMessage() == "lastName.max.size"

        where:

        email            | firstName   | lastName
        "test@gmail.com" | "firstName" | "annxfsdbzclkssinjowrzxyezalfobjxagffutbdgrufzdsijrkswlulbqzygseaggfnwuvnxjtjppzngeznhnnmixdraxwqwjrhbjsnvsvfpsmglnngocleipqugycvfbqvbhrhgqdnjvvhehddaojdicivlirgkjyolptjganzvimgqqbjuwqhopkrglormhrnrparffyqwirfuaienrcbwgypaiylfycutvyfwtmwenbtmjszrkociylponnb"
    }

    def "No validation error"() {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        then:
        assert violations.isEmpty() == true

        where:
        email            | firstName   | lastName
        "test@gmail.com" | "firstName" | "lastName"
    }
}
