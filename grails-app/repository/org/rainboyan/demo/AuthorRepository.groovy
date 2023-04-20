package org.rainboyan.demo


interface AuthorRepository {

    Author get(Serializable id)

    List<Author> list(Map args)

    Long count()

    void delete(Serializable id)

    Author save(Author author)

}