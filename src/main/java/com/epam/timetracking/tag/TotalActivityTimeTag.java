package com.epam.timetracking.tag;

import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.entity.Time;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class TotalActivityTimeTag extends TagSupport {
    private static final long serialVersionUID = 6730108640891969003L;
    private List<ActivityDTO> activities;
    private static final Logger log = LogManager.getLogger(TotalActivityTimeTag.class);

    @Override
    public int doStartTag() {
        Time totalActivityTime = activities.stream()
                .map(ActivityDTO::getTotalTime)
                .reduce(Time::sum).orElse(new Time());
        log.trace(totalActivityTime);
        try {
            pageContext.getOut().print(totalActivityTime);
        } catch (IOException e) {
            log.error("Cannot write total activity time: " + e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
