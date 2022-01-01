package com.game.service;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class PlayerSpecifications {
    public static Specification<Player> getPlayersByNameLikeSpec(String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                System.out.println("In specification name = " + name);
                if (name == null || name.length() == 0) {
                    System.out.println("return from if");
                    return criteriaBuilder.and();
                }
                System.out.println("after if");
                return criteriaBuilder.like(root.get("name"), name);
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
