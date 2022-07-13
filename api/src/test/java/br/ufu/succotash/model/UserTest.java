package br.ufu.succotash.model;

import static org.junit.jupiter.api.Assertions.*;

import br.ufu.succotash.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UserTest {
    @Autowired private UserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("Fulano da Silva", "fulanosilva", "fulanosilva", Role.CLIENT);
    }

    @AfterEach
    void tearDown() {
        repository.delete(user);
    }

    @Test
    public void givenUserRepository_shouldSaveUser_and_retrieveItById() {
        var userId = repository.save(user).getId();
        var retrievedUser = repository.findById(userId);

        assertTrue(retrievedUser.isPresent());
        var actualUser = retrievedUser.get();

        assertEquals(actualUser.getId(), user.getId());
        assertEquals(actualUser.getUsername(), user.getUsername());
        assertEquals(actualUser.getPassword(), user.getPassword());
        assertEquals(actualUser.getFullName(), user.getFullName());
        assertEquals(actualUser.getRole(), user.getRole());
    }

    @Test
    public void givenUserRepository_shouldSaveUser_and_deleteItById() {
        var userId = repository.save(user).getId();
        repository.deleteById(userId);
        var retrievedUser = repository.findById(userId);

        assertFalse(retrievedUser.isPresent());
    }

    @Test
    public void givenUserRepository_shouldSaveUser_and_editItById() {
        var userId = repository.save(user).getId();
        var retrievedUser = repository.findById(userId);

        assertTrue(retrievedUser.isPresent());
        var expectedUser = retrievedUser.get();

        expectedUser.setPassword("fulanosantos");
        expectedUser.setFullName("Fulano dos Santos");
        expectedUser.setUsername("fulanosantos");
        expectedUser.setRole(Role.WORKER);

        var actualUser = repository.save(expectedUser);

        assertEquals(actualUser.getId(), expectedUser.getId());
        assertEquals(actualUser.getUsername(), expectedUser.getUsername());
        assertEquals(actualUser.getPassword(), expectedUser.getPassword());
        assertEquals(actualUser.getFullName(), expectedUser.getFullName());
        assertEquals(actualUser.getRole(), expectedUser.getRole());
    }
}
