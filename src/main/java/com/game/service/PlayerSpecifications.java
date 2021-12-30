package com.game.service;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PlayerSpecifications {
    public static Specification<Player> getPlayersByNameLikeSpec(String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                System.out.println("predicate");
                Predicate nameLikePredicate;
                if("null".equals(name)) {
                    nameLikePredicate = criteriaBuilder.like(root.get("name"), "%");
                    System.out.println("all");}
                else nameLikePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                return nameLikePredicate;
            }
        };
    }

//    public static Specification<Employee> getEmployeesByPhoneTypeSpec(PhoneType phoneType) {
//        return new Specification<Employee>() {
//            @Override
//            public Predicate toPredicate(Root<Employee> root,
//                                         CriteriaQuery<?> query,
//                                         CriteriaBuilder criteriaBuilder) {
//                ListJoin<Employee, Phone> phoneJoin = root.join(Employee_.phones);
//                Predicate equalPredicate = criteriaBuilder.equal(phoneJoin.get(Phone_.type), phoneType);
//                return equalPredicate;
//            }
//        };
//    }
}
