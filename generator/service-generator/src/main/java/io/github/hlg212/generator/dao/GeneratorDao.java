package io.github.hlg212.generator.dao;

import io.github.hlg212.generator.model.bo.Col;
import io.github.hlg212.generator.model.bo.Table;

import java.util.List;

public interface GeneratorDao {

    public List<Table> getTableList(List tables);

    public List<Col> getColList(String tableName);
}
