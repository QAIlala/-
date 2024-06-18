package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/27 15:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditQuery {

    private Long id;

    private String status;

    private String reason;

}
