/* eslint-disable no-undef */
/* eslint-disable no-prototype-builtins */
/* eslint-disable @typescript-eslint/no-this-alias */
/* eslint-disable @typescript-eslint/no-empty-function */
// noinspection JSUnusedAssignment
/**
 * FG Timepicker 2019/2022 edition
 *
 * I am creating this timepicker script because I feel there is still in 2019-2022 a lack of a good usefull timepicker
 *
 * The previous one I created was based on jQuery UI. I think it is time we have a good plugin that have no dependencies.
 *
 */

var fg = fg || {};

fg.log =
  fg.log ||
  function log(log) {
    if (console) {
      //console.log(log);
    }
  };

fg.TPLocales = {
  en: {
    am: 'AM',
    pm: 'PM',
    hour: 'Hour',
    minute: 'Minute',
    close: 'Close',
    now: 'Now',
    unselect: 'Unselect',
  },
  fr: {
    am: 'AM',
    pm: 'PM',
    hour: 'Heure',
    minute: 'Minute',
    close: 'Fermer',
    now: 'Maintenant',
    unselect: 'Désélectionner',
  },
};

// a basic time object
fg.Time = function Time(hour, minute) {};

fg.Timepicker = function Timepicker(options) {
  let tpInst = this; // handle to this TP
  // init hour and minutes to current time
  let now = new Date();
  // the currently selected hour
  this.hour = now.getHours();
  // the currently selected minute
  this.minute = now.getMinutes();

  let mainElementClass = 'fgtp';
  // TODO: Add parameter for dark theme, which should add the .fgtp-dark to mainElementClass.

  // pointer to the popup element for popup behavior
  let popupEl = null;
  // pointer to the dom element build to show the timepicker
  let domEl = null;

  // Array of hour unit dom elements
  let domHourUnits = [];
  // Array of minute unit dom elements
  let domMinuteUnits = [];

  /**
   * processing option settings
   * The timepicker can be one and only one of the 3 : input, button or container
   */
  // bindInput is the text box HTMLInputElement bound to the timepicker for value and popup
  let bindInput = options.bindInput ? options.bindInput : null;
  // show the timepicker inline in this container
  let bindContainer = options.bindContainer ? options.bindContainer : null;
  // time options
  // timeSeparator : string that seperate hours and minutes
  // TODO: Move to locales
  this.timeSeparator = ':';
  // showHours
  this.showHours = true;
  // hoursStart and hoursEnd list available hours
  this.hoursStart = 0;
  this.hoursEnd = 23;
  // minutesStart, minutesEnd and minutesInterval list available minutes
  this.minutesStart = 0;
  this.minutesEnd = 59;
  this.minutesInterval = 5;
  // showMinutes
  this.showMinutes = true;
  // Animation flag, default false
  this.animatePopup = false;

  // Localisation :
  this.locale = 'en';

  // events
  // TODO: find a better way to affect all options to the instance object.
  this.onHourChange = null;
  this.onMinuteChange = null;
  this.onTimeChange = null;
  this.onShow = null;
  this.onHide = null;
  this.onRedraw = null;

  // internal to keep track of visibility of the timepicker
  let visible = false;

  // the options function is used to update options and parameters of timepicker instance
  this.options = function options(options) {
    for (const property in tpInst) {
      if (options.hasOwnProperty(property)) {
        tpInst[property] = options[property];
      }
    }

    // only redraw if function exists.  when initialising the timepicker, the function does not yet exists.
    if (this.hasOwnProperty('redraw')) {
      this.redraw();
    }
  };
  // call the options function to affect init options values to tp instance
  tpInst.options(options);

  // === getter and setters ===
  this.getHour = function getHour() {
    return tpInst.hour;
  };
  this.setHour = function setHour(newHour) {
    tpInst.hour = newHour;
    hourChangedEvent();
    timeChangedEvent();
  };

  this.getMinute = function getMinute() {
    return tpInst.minute;
  };
  this.setMinute = function setMinute(newMinute) {
    tpInst.minute = newMinute;
    minuteChangedEvent();
    timeChangedEvent();
  };

  this.setTime = function setTime(newHour, newMinute) {
    tpInst.hour = newHour;
    tpInst.minute = newMinute;
    hourChangedEvent();
    minuteChangedEvent();
    timeChangedEvent();
  };

  /**
   * function getFormattedTime
   * Return selected time displayed with localisation settings
   * TODO: implement localisation
   */

  this.getFormattedTime = function getFormattedTime() {
    return this.getHour() + tpInst.timeSeparator + this.getMinute();
  };

  /**
   * Parse given time string, return an object with hours and minutes.
   * This function is not string, it will find any two number seperated by a string
   *
   * The function will use instance time configuration properties to better interpret time.
   */
  this.parseTime = function parseTime(timeVal) {
    var retVal = new Object();
    retVal.hour = -1;
    retVal.minute = -1;

    if (!timeVal) return retVal;

    // first search for time seperator in string
    let p = timeVal.indexOf(tpInst.timeSeparator);
    // check if time separator found
    if (p != -1) {
      retVal.hour = parseInt(timeVal.substr(0, p), 10);
      retVal.minute = parseInt(timeVal.substr(p + 1), 10);
    }

    // check for hours only
    else if (tpInst.showHours && !tpInst.showMinutes) {
      retVal.hour = parseInt(timeVal, 10);
    }
    // check for minutes only
    else if (!tpInst.showHours && tpInst.showMinutes) {
      retVal.minute = parseInt(timeVal, 10);
    }
    /*
                if (showHours) {
                    var timeValUpper = timeVal.toUpperCase();
                    if ((retVal.hours < 12) && (showPeriod) && (timeValUpper.indexOf(amPmText[1].toUpperCase()) != -1)) {
                        retVal.hours += 12;
                    }
                    // fix for 12 AM
                    if ((retVal.hours == 12) && (showPeriod) && (timeValUpper.indexOf(amPmText[0].toUpperCase()) != -1)) {
                        retVal.hours = 0;
                    }
                }
        */
    return retVal;
  };

  // force redraw of the timepicker
  this.redraw = function redraw() {
    if (!visible) {
      return;
    }

    let tpDom = buildTPDom();

    if (bindInput && !bindContainer) {
      while (popupEl.firstChild) {
        //The list is LIVE so it will re-index each call
        popupEl.removeChild(popupEl.firstChild);
      }
      popupEl.appendChild(tpDom);
    }

    if (bindContainer) {
      while (bindContainer.firstChild) {
        //The list is LIVE so it will re-index each call
        bindContainer.removeChild(bindContainer.firstChild);
      }
      bindContainer.appendChild(tpDom);
    }
  };

  // hide / destroy the timepicker div element
  this.destroyPopup = function destroyPopup() {
    popupEl.remove();
    visible = false;
  };

  /**
   * Private functions
   */

  let hourChangedEvent = function () {
    highlightSelectedHour();
    if (tpInst.onHourChange) {
      tpInst.onHourChange.apply();
    }
  };
  let minuteChangedEvent = function () {
    highlightSelectedMinute();
    if (tpInst.onMinuteChange) {
      tpInst.onMinuteChange.apply();
    }
  };
  let timeChangedEvent = function () {
    if (tpInst.onTimeChange) {
      tpInst.onTimeChange.apply();
    }
    if (bindInput) {
      bindInput.value = tpInst.getFormattedTime();
    }
  };

  let highlightSelectedHour = function () {
    domHourUnits.forEach((unit, hour) => {
      if (hour === tpInst.getHour()) {
        unit.classList.add('selected');
      } else {
        unit.classList.remove('selected');
      }
    });
  };

  let highlightSelectedMinute = function () {
    domMinuteUnits.forEach((unit, minute) => {
      if (minute === tpInst.getMinute()) {
        unit.classList.add('selected');
      } else {
        unit.classList.remove('selected');
      }
    });
  };
  // create element helper function
  let e = function createElement(nameTag, className, bindToParent, innerHTML) {
    let e = document.createElement(nameTag);
    if (className) {
      e.className = className;
    }
    if (bindToParent) {
      bindToParent.appendChild(e);
    }

    if (innerHTML) {
      e.innerHTML = innerHTML;
    }

    return e;
  };
  this.e = e;

  let getLocale = function getLocale(text) {
    return fg.TPLocales[tpInst.locale][text];
  };

  /**
   * Build the DOM for the timepicker
   * @returns HTMLElement
   */
  let buildTPDom = function buildTPDom() {
    let classes = mainElementClass;
    if (bindContainer) {
      classes += ' inline';
    } else {
      classes += ' popup';
    }
    if (tpInst.animatePopup) {
      classes += ' animatePopup';
    }
    let newDomEl = e('div', classes);

    // set style position to just below input
    if (!bindContainer) {
      let top = bindInput.offsetTop + bindInput.offsetHeight;
      let left = bindInput.offsetLeft;
      newDomEl.style.position = 'absolute';
      newDomEl.style.left = left + 'px';
      newDomEl.style.top = top + 'px';
    }

    // prevent popup closing when clicking inside popup
    if (!bindContainer) {
      newDomEl.addEventListener('mousedown', function (e) {
        e.preventDefault();
        return false;
      });
    }

    let hoursBlock = e('div', 'hours', newDomEl);

    let amPmList = ['am', 'pm'];

    // todo: make the h4 configurable
    e('h4', 'fgtp-title', hoursBlock, getLocale('hour'));

    // empty dom hour unit array
    domHourUnits = [];

    for (let iAmPm = 0; iAmPm <= 1; iAmPm++) {
      let amPm = amPmList[iAmPm];
      let amPmBlock = e('div', 'fgtp-hr-block fgtp-ampm-block', hoursBlock); // TODO: add localisation

      e('h5', 'fgtp-ampm-title', amPmBlock, amPm.toUpperCase());

      let amUnitContainer = e(
        'div',
        'fgtp-unit-container fgtp-ampm-unit-container',
        amPmBlock
      );

      // Define first and end hour for the am/pm block
      let firstHour = iAmPm === 0 ? 0 : 12;
      let endHour = firstHour + 11;
      for (let i = firstHour; i <= endHour; i++) {
        // check if between starts and ends
        if (i < tpInst.hoursStart || i > tpInst.hoursEnd) {
          continue;
        }
        let hourUnit = e('div', 'fgtp-hour-unit fgtp-unit', amUnitContainer, i);
        if (i === 0) {
          hourUnit.innerText = '12'; // TODO: use 24 for 24hour display
        }
        hourUnit.onclick = function onUnitClick() {
          tpInst.setHour(i);
        };
        // append to hourUnits array
        domHourUnits[i] = hourUnit;
      }
    }
    highlightSelectedHour();

    const minutesBlock = e('div', 'minutes', newDomEl);
    const minutesTitle = e('h4', 'fgtp-title', minutesBlock, 'Minutes');
    let minutesUnitContainer = e(
      'div',
      'fgtp-unit-container fgtp-minutes-unit-container',
      minutesBlock
    );

    // empty minute unit array
    domMinuteUnits = [];

    for (
      let i = tpInst.minutesStart;
      i <= tpInst.minutesEnd;
      i += tpInst.minutesInterval
    ) {
      let minuteUnit = e(
        'div',
        'fgtp-minute-unit fgtp-unit',
        minutesUnitContainer,
        i.toString()
      );
      minuteUnit.onclick = function () {
        tpInst.setMinute(i);
      };
      // append to minutesUnits array
      domMinuteUnits[i] = minuteUnit;
    }
    highlightSelectedMinute();

    domEl = newDomEl;

    return domEl;
  };

  let inputChangeHandle = function () {
    newTime = tpInst.parseTime(bindInput.value);
    tpInst.setTime(newTime.hour, newTime.minute);
  };

  let inputFocusHandle = function (e) {
    if (bindContainer) {
      return;
    }

    // create a new element that will show as a popup
    popupEl = tpInst.e('div', 'fgtp');

    e.target.parentNode.insertBefore(popupEl, e.target.nextSibling);
    visible = true;
    tpInst.redraw();
  };

  let inputBlurHandle = function () {
    if (bindContainer) {
      return;
    }
    tpInst.destroyPopup();
  };

  // end of initialisation
  if (bindContainer) {
    bindContainer.appendChild(buildTPDom());
    visible = true;
    if (this.onShow) {
      this.onShow.apply();
    }
  }

  if (bindInput) {

    bindInput.addEventListener('change', inputChangeHandle);
    bindInput.addEventListener('focus', inputFocusHandle);
    bindInput.addEventListener('blur', inputBlurHandle);
  }

  return this;
};
export default fg;
