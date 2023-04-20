package org.rainboyan.demo

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AuthorController {

    AuthorRepository authorRepository

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        int size = Math.min(max ?: 10, 100)
        int page = (params.offset ?: 0) / size
        Pageable pageable = PageRequest.of(page, size)
        Page<Author> authorPage = authorRepository.findAll(pageable)
        respond authorPage.getContent(), model:[authorCount: authorPage.getTotalElements()]
    }

    def show(Long id) {
        respond authorRepository.findById(id).get()
    }

    def create() {
        Author author = new Author(params)
        respond author
    }

    def save(Author author) {
        if (author == null) {
            notFound()
            return
        }

        try {
            authorRepository.save(author)
        } catch (ValidationException e) {
            respond author.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect action: 'show', id: author.id
            }
            '*' { respond author, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond authorRepository.findById(id).get()
    }

    def update(Long id) {
        Author author = authorRepository.findById(id).get()
        if (author == null) {
            notFound()
            return
        }

        try {
            authorRepository.save(author)
        } catch (ValidationException e) {
            respond author.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect author
            }
            '*'{ respond author, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        Optional<Author> author = authorRepository.findById(id)
        author.ifPresent { authorRepository.delete(it) }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'author.label', default: 'Author'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
