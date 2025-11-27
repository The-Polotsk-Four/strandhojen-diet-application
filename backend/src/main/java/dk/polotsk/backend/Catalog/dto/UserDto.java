package dk.polotsk.backend.Catalog.dto;

import dk.polotsk.backend.Catalog.model.Userrole;

public record UserDto(Long id, Userrole userrole, String login, String name) {
}

