package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.model.ClsOrganization;

import java.util.List;

@Repository
public interface ClsOrganizationRepo extends JpaRepository<ClsOrganization, Long> {

    List<ClsOrganization> findAllByOrderByIdAsc();

    @Query(value = "with recursive organizations_from_parents as\n" +
            "       (\n" +
            "           select co.id, co.name, cast('{}' as int[]) as parents, 0 as level\n" +
            "           from cls_organization co\n" +
            "           where co.parent_id is NULL\n" +
            "\n" +
            "           union all\n" +
            "\n" +
            "           select c.id, c.name, p.parents || c.parent_id, p.level+1\n" +
            "           from      organizations_from_parents p\n" +
            "                         join cls_organization c\n" +
            "                              on c.parent_id = p.id\n" +
            "           where not c.id = any(p.parents)\n" +
            "       ),\n" +
            "        organizations_from_children as\n" +
            "       (\n" +
            "           select c.parent_id,\n" +
            "                  cast(json_agg(jsonb_build_object('value', c.name) || jsonb_build_object('id', c.path)) as jsonb) as js\n" +
            "           from organizations_from_parents tree\n" +
            "                    join cls_organization c using(id)\n" +
            "           where tree.level > 0 and not c.id = any(tree.parents)\n" +
            "           group by c.parent_id\n" +
            "\n" +
            "           union all\n" +
            "\n" +
            "           select c.parent_id,\n" +
            "                  jsonb_build_object('value', c.name)\n" +
            "                      || jsonb_build_object('data', tree.js)\n" +
            "                      || jsonb_build_object('id', c.path) as js\n" +
            "\n" +
            "           from organizations_from_children tree\n" +
            "                    join cls_organization c on c.id = tree.parent_id\n" +
            "       )\n" +
            "select jsonb_pretty(jsonb_agg(ofc.js))\n" +
            "from organizations_from_children ofc\n" +
            "where ofc.parent_id IS NULL;", nativeQuery = true)
    String getOrganizationTree();


}
