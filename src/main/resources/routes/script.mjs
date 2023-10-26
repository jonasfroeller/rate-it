import van from "https://cdn.jsdelivr.net/gh/vanjs-org/van/public/van-1.2.4.min.js";

const {li, ul, span, option, select, a, nav} = van.tags

const Navigation = ({items}) => ul({class: "flex gap-6 pt-4"}, items.map(it => li(it)))

van.add(document.getElementsByTagName("header")[0],
    nav(Navigation({items: [a({class: "hover:underline", href: "/q/swagger-ui/"}, "API Documentation"), a({class: "hover:underline", href: "/q/openapi"}, "Download API Documentation")]}))
);

const flattenRoutes = (routes, parentPath = "") => {
    return routes.reduce((acc, route) => {
        if (typeof route === "string") {
            const path = parentPath === "" ? route : `${parentPath}/${route}`;
            acc.push(path);
        } else if (Array.isArray(route)) {
            acc.push(...flattenRoutes(route, parentPath));
        } else if (typeof route === "object" && !Array.isArray(route)) {
            const key = Object.keys(route)[0];
            if (key) {
                const newPath = parentPath === "" ? key : `${parentPath}/${key}`;
                if (!Array.isArray(route[key])) {
                    acc.push(newPath);
                    acc.push(...flattenRoutes([route[key]], newPath));
                } else {
                    acc.push(...flattenRoutes(route[key], newPath));
                }
            }
        }

        return acc;
    }, []).filter((value, index, self) => self.indexOf(value) === index);
};

const RoutePreview = () => {
    const root_routes = ["software", "rating"];
    const software_routes = ["", "add",
        {"amount": ["",
                { "having":
                        ["description", "open-source", "repository", "website"]
                }
            ]
        }, "as-list",
        { "having":
                [{"description": ["as-list"]}, {"open-source": ["as-list"]}, {"repository": ["as-list"]}, {"website": ["as-list"]}]
        },
        "remove", "{softwareName}"
    ];
    const rating_routes = [];
    const endpoint_routes = [software_routes, rating_routes];

    const root_route = van.state("software"), route_path = van.state("");
    const availableRoutes = van.state([]);

    van.derive(() => {
        const routeIndex = root_routes.indexOf(root_route.val);
        availableRoutes.val = flattenRoutes(endpoint_routes[routeIndex]).map((path) => option({ value: path }, path));
    });

    return span(
        "Root: ",
        select({
                oninput: (e) => {
                    root_route.val = e.target.value;
                },
                value: root_route
            },
            root_routes.map(c => option({ value: c }, c)),
        ),
        " Path: ",
        select({
                oninput: (e) => {
                    route_path.val = e.target.value;
                },
                value: route_path
            },
            availableRoutes.val,
        ),
        span(" query URL: /api/", root_route, "/", route_path),
    );
};

van.add(document.getElementsByTagName("main")[0], RoutePreview());