export const $ = (selector: string) => {
  const result = document.querySelector(selector);
  if (!(result instanceof HTMLElement)) return null;

  return result;
};
