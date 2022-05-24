import {NavLink, Outlet} from "react-router-dom";

const Layout = () => {
    return (
        <>
            <nav>
                <ul>
                    <li>
                        <NavLink to='/'>Strona główna</NavLink>
                    </li>
                    <li>
                        <NavLink to='/login/google'>Zaloguj przez Google</NavLink>
                    </li>
                    <li>
                        <NavLink to='/login/github'>Zaloguj przez GitHub</NavLink>
                    </li>
                </ul>
            </nav>
            <Outlet/>
        </>
    );
};

export default Layout;