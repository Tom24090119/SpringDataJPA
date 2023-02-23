package com.example.springdatajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class SpringDataJpaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository ,
                                               StudentIdCardRepository studentIdCardRepository)
    {
        return args ->
        {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            int age = faker.number().numberBetween(17, 55);
            Student student1 = new Student(firstName,
                    lastName,
                    email,
                    age);
            Book book1 = new Book("Clean Code", LocalDateTime.now().minusDays(4));
            Book book2 = new Book("Think and Grow Rich", LocalDateTime.now());
            Book book3 = new Book("Spring Data Jpa", LocalDateTime.now().minusYears(1));

            student1.addBook(book1);
            student1.addBook(book2);
            student1.addBook(book3);

            Student student2 = new Student("Aaryaman","pol","aaryamanpol15@gmail.com",18);

            student2.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
            student2.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
            student2.addBook(book3);

            studentRepository.save(student2);


//            StudentIdCard studentIdCard = new StudentIdCard("123456789", student1);
//
//            student1.setStudentIdCard(studentIdCard);

//            student1.addEnrolment(new Enrolment(
//                    new EnrolmentId(1L,1L),
//                    student1,
//                    new Course("Computer Science" ,"IT"),
//                    LocalDateTime.now()
//            ));
//            student1.addEnrolment(new Enrolment(
//                    new EnrolmentId(1L,2L),
//                    student1,
//                    new Course("Amigoscode Spring Data JPA","IT"),
//                    LocalDateTime.now().minusDays(18)
//            ));


//            student1.enrolToCourse(new Course("Computer Science" ,"IT"));
//            student1.enrolToCourse(new Course("Amigoscode Spring Data JPA","IT"));


            studentRepository.save(student1);


            studentRepository.findById(1L)
                    .ifPresent(s -> {
                                System.out.println("fetch books lazy ...");
                                List<Book> books = student1.getBooks();
                                books.forEach(book ->{
                                    System.out.println(s.getFirstName()+" Borrowed " +book.getBookName());
                                });
                            });
//            studentRepository.findAllById(Arrays.asList(1L,2L)).forEach(s->{
//                List<Book> books = s
//            });

//            System.out.println("StudentIdCard Repo");
//            studentIdCardRepository.findById(1L).ifPresentOrElse(System.out::println,
//                    ()-> System.out.println("User does not exist"));

//            generateRandomStudents(studentRepository);

//            paging(studentRepository);

//            sorting(studentRepository);

//            Student maria = new Student("Maria"
//                    ,"Jones"
//                    ,"maria.jones@amigoscode.edu"
//                    ,21);
//
//            Student maria2 = new Student("Maria"
//                    ,"Jones"
//                    ,"maria2.jones@amigoscode.edu"
//                    ,25);
//
//            Student ahmed = new Student("Ahmed"
//                    ,"Ali"
//                    ,"ahmed.ali@amigoscode.edu"
//                    ,18);
//
//            System.out.println("Adding maria and ahmed");
//            studentRepository.saveAll(List.of(maria,ahmed,maria2));

//            System.out.println("Number of students");
//            System.out.println(studentRepository.count());
//
//            studentRepository.findById(3L).ifPresentOrElse(System.out::println,
//                    () -> System.out.println("Student with ID 3 not found")
//            );
//
//            System.out.println("Select all students");
//            List<Student> students= studentRepository.findAll();
//            students.forEach(System.out::println);
//
//            System.out.println("Delete maria ");
//            studentRepository.deleteById(1L);
//
//            System.out.println("Number of students");
//            System.out.println(studentRepository.count());

//            studentRepository.findStudentByEmail("ahmed.ali@amigoscode.edu")
//                    .ifPresentOrElse(System.out::println ,
//                            () -> System.out.println("Student with email ahmed.ali@amigoscode.edu not found"));
//
//            studentRepository.findStudentByFirstNameEqualsAndAgeGreaterThanEqual("maria",21)
//                    .forEach(System.out::println);
//
//            System.out.println("Deleting Maria ");
//            System.out.println(studentRepository.deleteStudentById(3L));
        };
    }

    private void paging(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0,
                5,
                Sort.by(Sort.Direction.ASC, "firstName"));

        Page<Student> page = studentRepository.findAll(pageRequest);
        page.getContent().forEach(System.out::println);
    }

    private void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName")  // DSC for descending
                .and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " +student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            int age = faker.number().numberBetween(17, 55);
            Student student = new Student(firstName,
                    lastName,
                    email,
                    age);
            studentRepository.save(student);
        }
    }

}