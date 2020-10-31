package hyx.plateform.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cases")
public class CaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "project")
    private String project;

    @Column(name = "folderDir")
    private String folderDir;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "standardDev")
    private String standardDev;

    @Column(name = "watchPic")
    private String watchPic;
}
