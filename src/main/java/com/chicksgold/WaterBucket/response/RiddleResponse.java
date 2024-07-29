package com.chicksgold.WaterBucket.response;

import com.chicksgold.WaterBucket.model.Solution;
import com.chicksgold.WaterBucket.model.Step;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiddleResponse {

    private List<Step> solution;

}
