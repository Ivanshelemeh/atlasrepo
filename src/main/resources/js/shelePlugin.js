AJS.toInit(function () {
    AJS.$('#valid').hide();
    AJS.$('#notify').hide();
    AJS.$('#form-1').submit(function (event) {
        AJS.$('#showAction').hide();
        event.preventDefault();
        let projects = $('#select2-example').val();
        if (projects === null) {
            AJS.$('#valid').show();
            AJS.$('#notify').hide();
        }
        let events = $('#event').val();
        if (events === null) {
            AJS.$('#valid').show();
            AJS.$('#notify').hide();
        }

        const settings = {
            project: projects,
            eventTypeIds: events
        }

        const request = jQuery.ajax({
            type: "POST",
            data: JSON.stringify(settings),
            url: AJS.contextPath() + "/rest/rest-issue/1.0/api/issue",
            headers: {'X-Atlassian-Token': 'nocheck'},
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            statusCode: {
                201: function () {
                    AJS.$('#notify').show();
                    if (projects === null || events === null) {
                        AJS.$('#notify').hide();
                    }
                }
            }
        });
    });
});