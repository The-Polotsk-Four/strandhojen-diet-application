package dk.polotsk.backend.Catalog.dto;

import dk.polotsk.backend.Catalog.model.Userrole;

public record UserCreateDto(Long id, String login, String password, Userrole userrole, String name){}
