package org.rainboyan.demo

import org.springframework.data.jpa.repository.config.EnableJpaRepositories

import grails.boot.Grails

import groovy.transform.CompileStatic

@CompileStatic
@EnableJpaRepositories(basePackageClasses = AuthorRepository)
class Application {

    static void main(String[] args) {
        Grails.run(Application, args)
    }

}
