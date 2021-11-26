package uz.sardorbek.warehouse.dataLoader;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.sardorbek.warehouse.entity.*;

import uz.sardorbek.warehouse.repository.*;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String mode;

    final
    ProductRepository productRepository;

    final
    MeassurementTypeRepository meassurementTypeRepository;

    final
    MeassurementValueRepository meassurementValueRepository;

    final
    ProductValueTypeRepository productValueTypeRepository;

    final
    UserRepository userRepository;

    final
    PasswordEncoder passwordEncoder;

    public DataLoader(ProductRepository productRepository, MeassurementTypeRepository meassurementTypeRepository, MeassurementValueRepository meassurementValueRepository, ProductValueTypeRepository productValueTypeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.meassurementTypeRepository = meassurementTypeRepository;
        this.meassurementValueRepository = meassurementValueRepository;
        this.productValueTypeRepository = productValueTypeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (mode.equals("create")) {

            User user = new User("admin", passwordEncoder.encode("admin123"));
            User savedUser = userRepository.save(user);
            savedUser.setRoles(Collections.singleton(Role.DIRECTOR));
            userRepository.save(savedUser);

            User user2 = new User("user", passwordEncoder.encode("user123"));
            User savedUser2 = userRepository.save(user2);
            savedUser2.setRoles(Collections.singleton(Role.USER));
            userRepository.save(savedUser2);


            List<MeassurementType> meassurementTypes = Arrays.asList(
                    new MeassurementType("kg"),
                    new MeassurementType("pieces")
            );
            meassurementTypeRepository.saveAll(meassurementTypes);

            List<MeassurementValue> meassurementValues = Arrays.asList(
                    new MeassurementValue("4"),
                    new MeassurementValue("5"),
                    new MeassurementValue("6"),
                    new MeassurementValue("7"),
                    new MeassurementValue("8"),
                    new MeassurementValue("10"),
                    new MeassurementValue("12"),
                    new MeassurementValue("14"),
                    new MeassurementValue("15"),
                    new MeassurementValue("20")
            );
            meassurementValueRepository.saveAll(meassurementValues);

            List<Product> products = Arrays.asList(
                    new Product("un", "15", LocalDate.of(2021, 5, 24)),
                    new Product("guruch", "5", LocalDate.of(2021, 6, 23)),
                    new Product("makaron", "11", LocalDate.of(2021, 7, 22)),
                    new Product("olma", "16", LocalDate.of(2021, 8, 21)),
                    new Product("nok", "18", LocalDate.of(2021, 9, 24)),
                    new Product("kartoshka", "12", LocalDate.of(2021, 10, 23)),
                    new Product("sabzi", "1", LocalDate.of(2021, 11, 8)),
                    new Product("pomidor", "3", LocalDate.of(2021, 12, 16)),
                    new Product("piyoz", "2", LocalDate.of(2021, 2, 1)),
                    new Product("bodring", "14", LocalDate.of(2021, 3, 15))
            );
            productRepository.saveAll(products);

            List<ProductValueType> productValueTypes = Arrays.asList(
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(1L),
                            productRepository.getById(1L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(2L),
                            productRepository.getById(2L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(3L),
                            productRepository.getById(3L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(2L),
                            meassurementValueRepository.getById(4L),
                            productRepository.getById(4L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(5L),
                            productRepository.getById(5L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(2L),
                            meassurementValueRepository.getById(6L),
                            productRepository.getById(6L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(7L),
                            productRepository.getById(7L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(2L),
                            meassurementValueRepository.getById(8L),
                            productRepository.getById(8L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(9L),
                            productRepository.getById(9L)
                    ),
                    new ProductValueType(
                            meassurementTypeRepository.getById(1L),
                            meassurementValueRepository.getById(10L),
                            productRepository.getById(10L)
                    )
            );
            productValueTypeRepository.saveAll(productValueTypes);


        }
    }
}
