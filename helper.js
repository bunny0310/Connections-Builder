const verifyFormat = (body) => {
    const requiredKeys = ["firstName", "lastName", "email", "password"];
    const inputKeys = Object.keys(body);
    const reducer = (accumulator, currentValue) => accumulator + currentValue;

    // O(1) since we have only 4 keys which is constant
    const correctFormat = inputKeys.sort().reduce(reducer) === requiredKeys.sort().reduce(reducer);
    return correctFormat;
}

module.exports = {verifyFormat};