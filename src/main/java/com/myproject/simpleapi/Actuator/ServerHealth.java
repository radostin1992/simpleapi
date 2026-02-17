package com.myproject.simpleapi.actuator;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerHealth {
    private ArrayList<String> groups;
    private String status;
}
