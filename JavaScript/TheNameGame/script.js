function normalizeUpper(str) {
  return str
    .toUpperCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");
}

function dedent(callSite, ...args) {
  function format(str) {
    let size = -1;

    return str.replace(/\n(\s+)/g, (m, m1) => {
      if (size < 0) size = m1.replace(/\t/g, "    ").length;

      return "\n" + m1.slice(Math.min(m1.length, size));
    });
  }

  if (typeof callSite === "string") return format(callSite);

  if (typeof callSite === "function")
    return (...args) => format(callSite(...args));

  let output = callSite
    .slice(0, args.length + 1)
    .map((text, i) => (i === 0 ? "" : args[i - 1]) + text)
    .join("");

  return format(output);
}

function capitalize(name) {
  return name.charAt(0).toUpperCase() + name.substring(1);
}

function makeLyricsForName(name) {
  name = capitalize(name);

  const first = normalizeUpper(name.charAt(0));
  const suffix = first.match(/[AEIOUY]/)
    ? name.toLowerCase()
    : name.substring(1);

  const bName = first == "B" ? suffix : "B" + suffix;
  const fName = first == "F" ? suffix : "F" + suffix;
  const mName = first == "M" ? suffix : "M" + suffix;

  return dedent`${name}!
                ${name}, ${name} bo ${bName}
                Bo bana fanna fo ${fName}
                Fee fi mo ${mName}
                ${name}!`;
}

function main() {
  const nameForm = document.querySelector("#nameForm");
  const nameInput = document.querySelector("#nameInput");
  const result = document.querySelector("#result");

  function generate() {
    let name = nameInput.value;
    if (!name) {
      name = "Lana";
      nameInput.value = name;
    }
    const lyrics = makeLyricsForName(name);
    result.innerText = lyrics;
  }

  generate();

  nameForm.onsubmit = (event) => {
    event.preventDefault();
    generate();
  };
}

main();
