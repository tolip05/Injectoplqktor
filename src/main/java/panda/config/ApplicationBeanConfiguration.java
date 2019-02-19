package panda.config;

import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.validation.Validation;
import javax.validation.Validator;

public class ApplicationBeanConfiguration {
    @Produces
    public EntityManager entityManager(){
        return Persistence.createEntityManagerFactory("panda")
                .createEntityManager();
    }

    @Produces
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
