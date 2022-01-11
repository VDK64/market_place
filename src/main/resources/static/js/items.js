"use strict";
let count = 0;

$(document).ready(() => {
    updateTables();
    setInterval(() => {
        if (count >= 2) {
            location.replace("/errorPage");
        }
        updateTables();
    }, 3000);
});

let findItems = () => {
    $(".flex-container.content").empty();
    updateTables();
}

let getTimeRemaining = (endtime) => {
    const total = Date.parse(endtime) - Date.parse(new Date());
    const minutes = Math.floor((total / 1000 / 60) % 60);
    const hours = Math.floor((total / (1000 * 60 * 60)) % 24);
    const days = Math.floor(total / (1000 * 60 * 60 * 24));
    return {
        total,
        days,
        hours,
        minutes
    };
}

let updateTables = () => {
    $(".spinner").remove();
    tablesRequest();
}

let tablesRequest = () => {
    let url = getRestUrl();
    $.getJSON(url, (result) => {
        if (!result.error) {
            count = 0;
            let tables = result.tables;
            tables.forEach(table => {
                let element = $("#" + table.id);
                if (element.length !== 0) {
                    updateElement(element, table);
                } else {
                    createElement(table);
                }
            });
        } else {
            location.replace("/errorPage");
        }
    });
    count++;
}

let getRestUrl = () => {
    let url = null;
    if (location.href.includes("/myItems")) {
        url = "/webapi/user-data/" + $(".principal").val();
    } else {
        let value = $(".searcher").val();
        if (value != null || value != "") {
            url = "/webapi/all-data/" + value;
        } else {
            url = "/webapi/all-data";
        }
    }
    return url;
}

let updateElement = (element, table) => {
    element.children(".title").children("h2").text(table.title);
    element.children(".description").text(table.description);
    let elementTable = element.children("table").children("tbody");
    elementTable.children(".seller").children(".value").text(table.seller);
    elementTable.children(".start-price").children(".value").text(table.startPrice);
    elementTable.children(".bid-inc").children(".value").text(table.bidInc);
    elementTable.children(".best-offer").children(".value").text(table.bestOffer);
    elementTable.children(".bidder").children(".value").text(table.bidder);
    let stopDate = remainString(table.stopDate);
    elementTable.children(".stop-date").children(".value").text(stopDate);
    if (stopDate === "Closed") {
        element.children(".make-bidd").children("form").children("button").attr("disabled", true);
    }
}

let createElement = (table) => {
    let remaining = getTimeRemaining(table.stopDate);
    let disabled = "";
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (remaining.total <= 0) {
        disabled = "disabled";
    }
    $(".flex-container").append(`
                <div id="${table.id}" class="flex-item">
                    <div class="titleImage">
                        <img src="/webapi/image/item/${table.id}" heigh="100" width="200" border="0">
                    </div>
                    <div class="title">
                        <h2>${table.title}</h2>
                    </div>
                     <div class="description item-style">${table.description}</div>
                    <table cellspacing="5px">
                        <tr class="seller">
                            <td class="key">Seller:</td>
                            <td class="value">${table.seller}</td>
                        </tr>
                        <tr class="start-price">
                            <td class="key">Start price:</td>
                            <td class="value">${table.startPrice}</td>
                        </tr>
                        <tr class="bid-inc">
                            <td class="key">Bid inc:</td>
                            <td class="value">${table.bidInc}</td>
                        </tr>
                        <tr class="best-offer">
                            <td class="key">Best offer:</td>
                            <td class="value">${table.bestOffer}</td>
                        </tr>
                        <tr class="bidder">
                            <td class="key">Bidder:</td>
                            <td class="value">${table.bidder}</td>
                        </tr>
                        <tr class="stop-date">
                            <td class="key">Remaining:</td>
                            <td class="value">${remainString(table.stopDate)}</td>
                        </tr>
                    </table>
                </div>`);
    if ($(".principal").val() != null) {
        if (table.user.email == $(".principal").val()) {
            $("#" + table.id).children("table")
                .after(`<div class="make-bidd">
                            <form action="/item" method="GET">
                                <input type="number" name="itemId" value=${table.id} hidden />
                                <button class="button blue-button" type="submit">Edit</button>
                            </form>
                        </div>`);
        } else {
            $("#" + table.id).children("table")
                .after(`<div class="make-bidd">
                        <form action="/bid" method="POST">
                            <input name="amount" type="number" step=${table.bidInc} min=${table.bestOffer + table.bidInc} required />
                            <input name="itemId" type="number" value="${table.id}" hidden />
                            <input type="hidden" name="${header}" value="${token}" />
                            <button class="button green-button" ${disabled}>Make bidd!</button>
                        </form>
                    </div>`);
        }
    }
}

let remainString = (date) => {
    let remaining = getTimeRemaining(date);
    if (remaining.total <= 0) {
        return "Closed";
    } else {
        return `${remaining.days} D ${remaining.hours} H ${remaining.minutes + 1} M`;
    }
}