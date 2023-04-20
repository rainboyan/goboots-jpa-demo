package org.rainboyan.demo

import org.springframework.data.repository.PagingAndSortingRepository

interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

}