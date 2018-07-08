package com.resfeber.pool.data.repository

import com.resfeber.pool.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification

@Profile("test")
@SpringBootTest
class UserRepositorySpec extends Specification {
    @Autowired
    UserRepository userRepository;

    def "save user"(email, firstName, lastName) {
        given:
        User user = User.builder().email(email).firstName(firstName).lastName(lastName).build()
        when:
        user = userRepository.saveAndFlush(user)
        then:
        assert user

        where:
        email           | firstName   | lastName
        "test@gmil.com" | "firstName" | "lastName"
    }

}
