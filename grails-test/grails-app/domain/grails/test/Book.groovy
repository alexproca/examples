package grails.test

import grails.rest.Resource

@Resource(uri = '/books', formats = ['json', 'xml'])
class Book {

    String title

    static constraints = {
        title blank: false
    }
}
