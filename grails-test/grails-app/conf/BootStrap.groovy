import grails.test.Book

class BootStrap {

    def init = { servletContext ->
        new Book(title: "The Stand").save()
        new Book(title: "The Shinning").save()
    }
    def destroy = {
    }
}
