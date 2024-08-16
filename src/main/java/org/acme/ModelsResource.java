package org.acme;

import java.util.List;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/models")
public class ModelsResource {

    @GET
    public List<Model> get() {
        return Model.listAll();
    }

    @GET
    @Path("/{id}")
    public Model get(@RestPath int id) {
        return Model.find("id", id).singleResult();
    }

    @POST
    @Transactional
    public Model create(@RestForm String name) {
        var model = new Model();
        model.name = name;
        model.persist();
        return model;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public boolean delete(@RestPath int id) {
        return Model.deleteById(id);
    }

}
