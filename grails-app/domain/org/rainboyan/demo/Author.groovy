package org.rainboyan.demo

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Version
import javax.validation.constraints.NotNull

import static javax.persistence.GenerationType.AUTO

@CompileStatic
@Entity
@Table(name = 'author')
class Author {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long id

    @Version
    Long version

    @NotNull
    @Column(name = 'name', nullable = false, unique = true)
    String name

    Author(Map args) {
        this.id = (Long) args?.id
        this.name = args?.name
    }

    Author() {}
}
