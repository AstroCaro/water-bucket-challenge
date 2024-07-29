package com.chicksgold.WaterBucket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Data
public class Solution {

    private List<Step> steps;

}
