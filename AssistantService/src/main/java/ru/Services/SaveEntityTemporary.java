package ru.Services;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.models.OperatorId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SaveEntityTemporary  {
  private List<Map<String, OperatorId>> entities = new ArrayList<Map<String, OperatorId>>();
}