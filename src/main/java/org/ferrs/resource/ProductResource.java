package org.ferrs.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.ferrs.dtos.ProductRequestDto;
import org.ferrs.dtos.ProductResponseDto;
import org.ferrs.service.ProductService;

@Path("/products")
@Tag(name = "Products", description = "CRUD operations for products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

  @Inject
  ProductService service;

  @POST
  @Operation(summary = "Create a product", description = "Registers a new product in the system")
  public Response create(ProductRequestDto dto) {
    ProductResponseDto response = service.create(dto);
    return Response.status(Response.Status.CREATED).entity(response).build();
  }

  @GET
  @Operation(summary = "List all products", description = "Returns a list of all products")
  public List<ProductResponseDto> listAll() {
    return service.listAll();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Get product by id", description = "Returns a product by its id")
  public Response findById(@PathParam("id") Long id) {
    try {
      ProductResponseDto response = service.findById(id);
      return Response.ok(response).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }

  @PUT
  @Path("/{id}")
  @Operation(summary = "Update a product", description = "Updates an existing product by id")
  public Response update(@PathParam("id") Long id, ProductRequestDto dto) {
    try {
      ProductResponseDto response = service.update(id, dto);
      return Response.ok(response).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Operation(summary = "Delete a product", description = "Deletes a product by its id")
  public Response delete(@PathParam("id") Long id) {
    try {
      service.delete(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }

}
