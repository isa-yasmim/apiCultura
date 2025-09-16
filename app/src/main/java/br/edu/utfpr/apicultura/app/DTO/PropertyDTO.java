package br.edu.utfpr.apicultura.app.dto;

import java.util.List;

public record PropertyDTO (String address, String number, String name, String description, List<HiveDTO> hives) {}
