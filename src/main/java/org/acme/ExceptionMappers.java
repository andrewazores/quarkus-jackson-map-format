package org.acme;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.persistence.NoResultException;

public class ExceptionMappers {

    @ServerExceptionMapper
    public RestResponse<Void> mapNoResultException(NoResultException ex) {
        return RestResponse.notFound();
    }

}
