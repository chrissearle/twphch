/*
 * Copyright 2010 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

var mouseX = 0;
var mouseY = 0;

$(document).ready(function () {
    $("a.toggleLink").bind("click", function(e) {
        $("#photobox_" + $(this).attr("id")).toggle("slow");
    }).show();

    $("input[type='checkbox']").bind("click", function(e) {
        flashScore();
    });

    $(document).mousemove(function(e) {
        mouseX = e.pageX;
        mouseY = e.pageY;
    });
});

function flashScore() {
    score = $('#score');

    voteCount = $("input[type='checkbox']:checked").size();

    score.text(voteCount);

    // This check does not take into account if there are 5 pics and the voter is one of the photographers
    // or if there are less than 5 pics. Submit validation does this correctly. Need to migrate the check
    // to javascript here too.
    if (voteCount > 5) {
        if (score.hasClass('ok')) {
            score.removeClass('ok');
        }
        if (!score.hasClass('nok')) {
            score.addClass('nok');
        }
    } else {
        if (score.hasClass('nok')) {
            score.removeClass('nok');
        }
        if (!score.hasClass('ok')) {
            score.addClass('ok');
        }
    }

    score.css('left', (mouseX + score.height()));
    score.css('top', (mouseY - (2 * score.height())));

    score.fadeIn();
    score.fadeOut();
}

