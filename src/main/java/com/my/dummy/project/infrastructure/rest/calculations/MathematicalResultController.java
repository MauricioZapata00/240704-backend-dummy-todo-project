package com.my.dummy.project.infrastructure.rest.calculations;

import com.my.dummy.project.application.useCase.calculations.SaveMathematicalOperationUseCase;
import com.my.dummy.project.domain.exceptions.calculations.InvalidOperationException;
import com.my.dummy.project.infrastructure.rest.calculations.dto.NumbersAndOperationDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/api/v1/calculations/mathematical-result")
@RequestScoped
@RequiredArgsConstructor
public class MathematicalResultController {

    private final SaveMathematicalOperationUseCase saveMathematicalOperationUseCase;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> saveMathematicalOperation(NumbersAndOperationDTO numbersAndOperationDTO) {
        log.info("saving MathematicalOperation {}", numbersAndOperationDTO);
        return this.saveMathematicalOperationUseCase.process(numbersAndOperationDTO.getFirstNumber(),
                        numbersAndOperationDTO.getSecondNumber(), numbersAndOperationDTO.getOperation())
                .map(mathematicalOperation -> Response.status(Response.Status.CREATED).entity(mathematicalOperation).build())
                .onFailure().recoverWithItem(this::validateException);
    }

    private Response validateException(Throwable throwable) {
        log.error(throwable.getMessage());
        Response.Status statusResponse = Response.Status.INTERNAL_SERVER_ERROR;
        if (InvalidOperationException.class.equals(throwable.getClass())){
            statusResponse = Response.Status.BAD_REQUEST;
        }

        return Response.status(statusResponse)
                .entity(throwable.getMessage())
                .build();
    }
}
