//package com.michaelklanica.listology.services;
//
//import com.michaelklanica.listology.models.Post;
//import com.michaelklanica.listology.models.User;
//import com.michaelklanica.listology.repos.PostRepository;
//import com.michaelklanica.listology.repos.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class DatabaseSeeder implements CommandLineRunner {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private final PostRepository postDao;
//    private final UserRepository userDao;
//
//    @Value("${app.env}")
//    private String environment;
//
//    public DatabaseSeeder(PostRepository postDao, UserRepository userDao) {
//        this.postDao = postDao;
//        this.userDao = userDao;
//    }
//
//    // generate a list of users and return it after saving
//    private List<User> seedUsers() {
//        List<User> users = Arrays.asList(
//                new User("lee@lee.com","Lee","password"),
//                new User("evan@evan.com","Evan","password"),
//                new User("chris@chris.com","Chris","password"),
//                new User("mike@mike.com","Mike","password"),
//                new User("jeff@jeff.com","Jeff","password")
//                );
//        userDao.saveAll(users);
//        return users;
//    }
//
//    // generate a handful of posts, and randomly assign a user to each one
//    private void seedPosts(List<User> users) {
//        Post longPost = new Post(
//                "Example 1",
//                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci atque commodi eligendi necessitatibus voluptates. At distinctio dolores minus molestiae mollitia nemo sapiente ut veniam voluptates! Corporis distinctio error quaerat vel!"
//        );
//        List<Post> posts = Arrays.asList(
//                new Post("Title 1", "Body 1"),
//                new Post("Title 2", "Body 2"),
//                new Post("Title 3", "Body 3"),
//                new Post("Title 4", "Body 4"),
//                new Post("Example 2", "QWE Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci atque commodi eligendi necessitatibus voluptates. At distinctio dolores minus molestiae mollitia nemo sapiente ut veniam voluptates! Corporis distinctio error quaerat vel!"),
//                new Post("Example 3", "ASD Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci atque commodi eligendi necessitatibus voluptates. At distinctio dolores minus molestiae mollitia nemo sapiente ut veniam voluptates! Corporis distinctio error quaerat vel!"),
//                longPost
//        );
//        Random r = new Random();
//        for (Post p : posts) {
//            User randomUser = users.get(r.nextInt(users.size()));
//            p.setAuthor(randomUser);
//        }
//        postDao.saveAll(posts);
//    }
//
//    @Override
//    public void run(String... strings) throws Exception {
//        if (! environment.equals("development")) {
//            log.info("app.env is not development, doing nothing.");
//            return;
//        }
//        log.info("Deleting posts...");
//        postDao.deleteAll();
//        log.info("Deleting users...");
//        userDao.deleteAll();
//        log.info("Seeding users...");
//        List<User> users = seedUsers();
//        log.info("Seeding posts...");
//        seedPosts(users);
//        log.info("Finished running seeders!");
//    }
//}
