
function createCheckbox(id) {
    const wrapper = document.createElement("div");
    wrapper.classList.add("checkbox");

    const input = document.createElement("input");
    input.type = "checkbox"
    input.id = id;

    const label = document.createElement("label");
    label.htmlFor = id;

    wrapper.appendChild(input);
    wrapper.appendChild(label);

    return { wrapper, input, label };
}

function randomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function randomElt(array) {
    const index = Math.floor(Math.random() * array.length);
    return array[index];
}

function shuffle(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function onChange(current, allInputs) {
    if (current.checked) {
        return;
    }
    const otherOffInputs = allInputs.filter(input => input !== current && !input.checked);
    shuffle(otherOffInputs);

    const onInputsCount = allInputs.filter(input => input.checked).length;

    if (onInputsCount < 2) {
        const n = randomElt([1, 1, 1, 1, 1, 1, 2, 2, 3]);
        otherOffInputs.slice(0, n).forEach(input => {
            input.checked = true;
        });
    }
}

function main() {
    const container = document.querySelector("#container");
    const allInputs = [];
    const N = 48;

    for (let i = 0; i < N; i++) {
        const { wrapper, input } = createCheckbox("checkbox" + i);
        container.appendChild(wrapper);
        allInputs.push(input);
        input.onchange = () => onChange(input, allInputs);
    }

    const n = randomInt(0, N);
    allInputs[n].checked = true;
}

main();