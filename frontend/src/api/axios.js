import axios from "axios";

/*
Axios instance = custom configurationLike creating your own HTTP client
*/
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

/*
INTERCEPTOR (request)This runs BEFORE every API call
*/
api.interceptors.request.use(
  (config) => {

    // Get token from localStorage
    const token = localStorage.getItem("token");
    console.log("TOKEN:", localStorage.getItem("token"));

    // If token exists → attach to header
    if (token) {
      config.headers.Authorization = "Bearer " + token;
    }

    // Return modified request
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
/*
RESPONSE INTERCEPTOR
→ runs when response comes back
*/
api.interceptors.response.use(
  (response) => response, // normal flow

  async (error) => {

    console.log("❗ Interceptor triggered");
    const originalRequest = error.config;
    console.log("Status:", error.response?.status);
    console.log("Original request:", originalRequest.url);

    // If token expired (401) and not already retried
    if (
  error.response &&
  (error.response.status === 401 || error.response.status === 403) &&
  !originalRequest._retry
) {

      originalRequest._retry = true; // prevent infinite loop

      try {
        // Get refresh token
        console.log("Interceptor triggered:", error.response?.status);

        const refreshToken = localStorage.getItem("refreshToken");
        console.log("Refresh token:", refreshToken);

        // Call refresh API
        const res = await axios.post("http://localhost:8080/auth/refresh", {
          refreshToken: refreshToken
        });
          console.log("FULL REFRESH RESPONSE:", res.data);
        // Get new access token
        const newToken = res.data.accessToken;
        // Save new token
        localStorage.setItem("token", newToken);
        localStorage.setItem("refreshToken", res.data.refreshToken);

        // Update header
        originalRequest.headers.Authorization = "Bearer " + newToken;

        // Retry original request
        console.log("🔁 Retrying original request");
        return api(originalRequest);

      } catch (refreshError) {

        console.error("Refresh failed", refreshError);

        // If refresh fails → logout
        localStorage.clear();
        window.location.href = "/";

        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);
export default api;