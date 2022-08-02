package br.ufu.succotash.domain.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String field;
    private Class<?> entity;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        Query query = entityManager.createQuery("select 1 from "
                + entity.getName() + " where " + field + " = :value");

        query.setParameter("value", value);

        return query.getResultList().isEmpty();
    }
}
